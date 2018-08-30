package com.dolo.wechat.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

public class LuceneUtil {
    private static final Logger logger = LogManager.getLogger(LuceneUtil.class);

    private static final CJKAnalyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);

    /**
     * @param indexPath   创建索引的文件夹
     * @param keyValueMap 批量创建索引文件 key value 形式 例如 ("标题1","内容1"),("标题2","内容2")
     * @throws IOException
     */
    public static void createIndex(String indexPath, Map<String, String> keyValueMap) throws IOException {

        Directory dir = null;
        File file = new File(indexPath);
        IndexWriter iwriter = null;
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
        if (keyValueMap == null) {
            return;
        }
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        dir = FSDirectory.open(file);
        if (IndexWriter.isLocked(dir)) {
            IndexWriter.unlock(dir);
        }
        iwriter = new IndexWriter(dir, config);
        for (String key : keyValueMap.keySet()) {
            Document doc = new Document();
            doc.add(new Field("id", key, TextField.TYPE_STORED));
            doc.add(new Field("content", keyValueMap.get(key), TextField.TYPE_STORED));
            iwriter.addDocument(doc);
        }
        iwriter.close();
        dir.close();

    }


    /**
     * @param indexPath 索引文件路径
     * @param keyWords  需要查询的关键字
     * @param flag      如果为true 则返回文本内容,否则默认返回内容对应标题
     * @return
     * @throws IOException
     */
    public static String queryByLucene(String indexPath, String keyWords, boolean flag) throws IOException {
        File file = new File(indexPath);
        Directory dir = null;
        DirectoryReader ireader = null;
        String content = "";
        String result = "";
        dir = FSDirectory.open(file);
        ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        QueryBuilder parser = new QueryBuilder(analyzer);
        Query query = parser.createBooleanQuery("content", keyWords);
        ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
        for (ScoreDoc scoreDoc : hits) {
            Document hitDoc = isearcher.doc(scoreDoc.doc);
            result = hitDoc.get("id");
            content = hitDoc.get("content");
        }
        ireader.close();
        dir.close();
        if (flag) {
            result = content;
        }
        return result;
    }

    /**
     * @param indexPath 索引文件夹
     * @param map       需要从表里获得所有 需要创建lucene索引的数据,并生成map 因为修改索引文件需要重新删除,再创建,这样也能保证定期更新有问题的索引文件
     * @throws IOException
     */
    public static void delAll(String indexPath, String id) throws IOException {
        IndexWriter iwriter = null;
        Directory dir = null;
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
        File file = new File(indexPath);
        dir = FSDirectory.open(file);
        if (IndexWriter.isLocked(dir)) {
            IndexWriter.unlock(dir);
        }
        iwriter = new IndexWriter(dir, config);
        iwriter.deleteAll();
        iwriter.commit();
        iwriter.close();
        dir.close();
    }

}

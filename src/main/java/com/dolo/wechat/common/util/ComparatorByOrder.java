package com.dolo.wechat.common.util;

import com.dolo.wechat.manage.entity.TreeNode;
import java.util.Comparator;

public class ComparatorByOrder implements Comparator<TreeNode>
{
    
    // 菜单排序内部类，order值越小越考前

        public int compare(TreeNode node1, TreeNode node2)
        {
            return (int)(node1.getOrder() - node2.getOrder());
        }

}

package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.EventMessageJournal;
import com.dolo.wechat.mapper.EventMessageJournalMapper;
import com.dolo.wechat.service.IEventMessageJournalService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class EventMessageJournalServiceImpl extends ServiceImpl<EventMessageJournalMapper, EventMessageJournal> implements IEventMessageJournalService {

    @Autowired
    private EventMessageJournalMapper eventMessageJournalMapper;

    @Override
    public int insertEventMessageJournal(EventMessageJournal eventMessageJournal) {
        return eventMessageJournalMapper.insert(eventMessageJournal);
    }
}

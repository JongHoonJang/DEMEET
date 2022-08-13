package com.ssafy.api.service;

import com.ssafy.common.customException.ConferenceNullException;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.repository.ConferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("ConferencesService")
public class ConferencesServiceImpl implements ConferencesService{

    @Autowired
    ConferencesRepository conferencesRepository;
    @Override
    public Conferences findConferencesBySessionNameAndActivation(String openviduSessionId) throws ConferenceNullException {
        log.info("enter getConferencesByOpenviduSessionId");
        Conferences conference = conferencesRepository.findConferencesBySessionNameAndActivation(openviduSessionId, true)
                .orElseThrow(()->new ConferenceNullException("can not find conference by session name " + openviduSessionId));
        return conference;
    }
}

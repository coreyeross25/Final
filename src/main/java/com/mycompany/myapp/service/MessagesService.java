package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Channels;
import com.mycompany.myapp.domain.Messages;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.ChannelsRepository;
import com.mycompany.myapp.repository.MessagesRepository;
import com.mycompany.myapp.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

    MessagesRepository repository;
    UserProfileRepository profileRepository;
    ChannelsRepository channelsRepository;

    public MessagesService(
        @Autowired MessagesRepository repository,
        @Autowired UserProfileRepository profileRepository,
        @Autowired ChannelsRepository channelsRepository
    ) {
        this.repository = repository;
        this.profileRepository = profileRepository;
        this.channelsRepository = channelsRepository;
    }

    //    public MessagesService(@Autowired UserProfileRepository profileRepository){
    //        this.profileRepository = profileRepository;
    //    }
    //
    //    public MessagesService(@Autowired ChannelsRepository channelsRepository){
    //        this.channelsRepository = channelsRepository;
    //    }

    public List<Messages> getMessagesByChannel(Long id) {
        return repository.findAllMessagesByChannelId(id);
    }

    public Messages postMessagesByChannel(Long channelID, Long userProfileID, Messages messages) {
        Channels channels = new Channels();
        UserProfile userProfile = new UserProfile();
        channels.setId(channelID);
        userProfile.setId(userProfileID);

        messages.setChannels(channels);
        messages.setUserProfile(userProfile);

        return repository.save(messages);
    }

    public Messages postMessagesByUserAndChannel(Long channelID, String username, Messages messages) {
        Channels channels = channelsRepository.findById(channelID).orElseThrow(() -> new EntityNotFoundException("Channel not found"));

        UserProfile userProfile = profileRepository.findByUsername(username);

        messages.setChannels(channels);
        messages.setUserProfile(userProfile);

        return repository.save(messages);
    }
}

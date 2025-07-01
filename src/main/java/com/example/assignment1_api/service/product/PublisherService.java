package com.example.assignment1_api.service.product;


import com.example.assignment1_api.dto.request.product.PublisherCreateRequest;
import com.example.assignment1_api.dto.response.product.PublisherResponse;
import com.example.assignment1_api.entity.product.Publisher;
import com.example.assignment1_api.mapper.PublisherMapper;
import com.example.assignment1_api.repository.PublisherRepository;
import com.example.assignment1_api.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublisherService {
    PublisherRepository publisherRepository;
    StringUtils stringUtils;
    PublisherMapper publisherMapper;

    public PublisherResponse createPublisher(PublisherCreateRequest request) {
        request.setName(stringUtils.getExactName(request.getName()));

        log.info("Creating publisher " + request.getName());
        if(publisherRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Publisher already exists!");
        }
        Publisher publisher = publisherMapper.toPublisher(request);
        return publisherMapper.toPublisherResponse(publisherRepository.save(publisher));
    }
}

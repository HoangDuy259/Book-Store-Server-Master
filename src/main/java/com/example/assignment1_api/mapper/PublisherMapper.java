package com.example.assignment1_api.mapper;

import com.example.assignment1_api.dto.request.product.PublisherCreateRequest;
import com.example.assignment1_api.dto.response.product.PublisherResponse;
import com.example.assignment1_api.entity.product.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherCreateRequest request);

    PublisherResponse toPublisherResponse(Publisher publisher);
}

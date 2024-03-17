package edu.java.mapper;

import edu.java.domain.model.Link;
import edu.java.dto.api.response.LinkResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper extends GenericMapper<Link, LinkResponse> {
    protected LinkMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected Class<LinkResponse> getDtoClass() {
        return LinkResponse.class;
    }
}

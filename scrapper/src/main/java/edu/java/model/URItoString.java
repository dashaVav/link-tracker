package edu.java.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter
public class URItoString implements AttributeConverter<URI, String> {

    @Override
    public String convertToDatabaseColumn(URI url) {
        return url.toString();
    }

    @Override
    public URI convertToEntityAttribute(String url) {
        return URI.create(url);
    }
}

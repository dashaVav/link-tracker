package edu.java.domain.model;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {
    private long id;
    private URI url;
    private OffsetDateTime checkedAt;
}

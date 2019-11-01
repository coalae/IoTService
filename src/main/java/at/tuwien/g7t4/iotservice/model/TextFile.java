package at.tuwien.g7t4.iotservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextFile {
    private String fileName;
    private String url;
    private String text;
}

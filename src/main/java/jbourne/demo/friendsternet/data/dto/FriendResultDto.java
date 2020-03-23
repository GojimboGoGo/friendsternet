package jbourne.demo.friendsternet.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendResultDto {
    private Boolean success;
    private List<String> friends;
    private List<String> recipients;
    private List<String> messages;
    private Integer count;
}

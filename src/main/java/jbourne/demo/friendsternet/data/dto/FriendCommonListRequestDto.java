package jbourne.demo.friendsternet.data.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Data
public class FriendCommonListRequestDto {
    private @NonNull List<String> friends;
}

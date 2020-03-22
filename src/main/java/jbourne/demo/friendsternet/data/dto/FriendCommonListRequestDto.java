package jbourne.demo.friendsternet.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@Data
public class FriendCommonListRequestDto {
    private @NonNull List<String> friends;
}

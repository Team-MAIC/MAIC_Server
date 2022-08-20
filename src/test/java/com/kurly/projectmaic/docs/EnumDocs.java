package com.kurly.projectmaic.docs;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {
	Map<String, String> ResponseCode;
}

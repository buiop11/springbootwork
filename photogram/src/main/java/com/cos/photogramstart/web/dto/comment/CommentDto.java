package com.cos.photogramstart.web.dto.comment;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;


// NotNull = Null 값체크 
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null 체크 그리고 빈 공백(스페이스까지)

@Data
public class CommentDto {

	@NotBlank  // 빈값이거나 null, 빈공백 체크 
	private String content;
	@NotNull 
	private Integer imageId;   // null만 체크 
	
	// toEntity가 필요없다.
	
}

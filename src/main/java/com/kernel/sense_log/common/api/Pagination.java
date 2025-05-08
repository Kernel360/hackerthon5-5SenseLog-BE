package com.kernel.sense_log.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {

  private Integer page;

  private Integer size;

  private Integer currentElements;

  private Integer totalPage;

  private Long totalElements;
}
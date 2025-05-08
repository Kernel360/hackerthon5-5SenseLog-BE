package com.kernel.sense_log.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

  private int page;
  private int size;
  private int currentElements;
  private long totalElements;
  private int totalPage;

}

package com.limito.user_service.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableUtils {
	private static final List<Integer> ALLOWED_SIZES = List.of(10, 30, 50);
	private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

	public static Pageable enforce(Pageable pageable) {
		return enforce(pageable, DEFAULT_SORT);
	}

	public static Pageable enforce(Pageable pageable, Sort defaultSort) {
		int page = Math.max(0, pageable.getPageNumber());
		int size = ALLOWED_SIZES.contains(pageable.getPageSize()) ? pageable.getPageSize() : 10;
		Sort sort = pageable.getSort().isUnsorted() ? defaultSort : pageable.getSort();

		return PageRequest.of(page, size, sort);
	}

	private PageableUtils() {
	}

}

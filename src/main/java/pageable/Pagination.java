package pageable;

import exception.ValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pagination {
    public static PageRequest makePageRequest(Integer from, Integer size, Sort sort) {
        if (size == null || from == null) {
            return null;
        }
        if (size <= 0 || from < 0) {
            throw new ValidationException("Ошибка параметров страницы. ");
        }
        return PageRequest.of(from / size, size, sort);
    }

    public static PageRequest makePageRequest(Integer from, Integer size) {
        if (size == null || from == null) {
            return null;
        }
        if (size <= 0 || from < 0) {
            throw new ValidationException("Ошибка параметров страницы. ");
        }
        return PageRequest.of(from / size, size);
    }
}

package ci.trabrouss.saas.services;


import ci.trabrouss.saas.common.PageResponse;

public interface BasicService<I, O> {

  void create(final I request);

  void update(final String id, I request);

  PageResponse<O> findAll(final int page, final int size);

  O findById(final String id);

  void delete(final String id);

}

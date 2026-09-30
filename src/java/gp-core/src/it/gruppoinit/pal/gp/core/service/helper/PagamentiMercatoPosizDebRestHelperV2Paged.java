package it.gruppoinit.pal.gp.core.service.helper;

import java.util.List;

public class PagamentiMercatoPosizDebRestHelperV2Paged {

    private List<PagamentiMercatoPosizDebRestHelperV2> items;
    private Integer totalCount;
    private Integer totalPages;

    public PagamentiMercatoPosizDebRestHelperV2Paged(List<PagamentiMercatoPosizDebRestHelperV2> items, Integer totalCount, Integer totalPages) {

	this.items = items;
	this.totalCount = totalCount;
	this.totalPages = totalPages;
    }

    public int getTotalCount() {

	return totalCount;
    }

    public void setTotalCount(int totalCount) {

	this.totalCount = totalCount;
    }

    public int getTotalPages() {

	return totalPages;
    }

    public void setTotalPages(int totalPages) {

	this.totalPages = totalPages;
    }

    public List<PagamentiMercatoPosizDebRestHelperV2> getItems() {

	return items;
    }

    public void setItems(List<PagamentiMercatoPosizDebRestHelperV2> items) {

	this.items = items;
    }

    @Override
    public String toString() {

	StringBuilder builder = new StringBuilder();
	builder.append("PagamentiMercatoPosizDebRestHelperV2Paged [items=");
	builder.append(items);
	builder.append(", totalCount=");
	builder.append(totalCount);
	builder.append(", totalPages=");
	builder.append(totalPages);
	builder.append("]");
	return builder.toString();
    }
}

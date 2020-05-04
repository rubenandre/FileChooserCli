package FileFilters;

import java.io.FileFilter;

public interface FileTypeFilterFabric {
    FileFilter makeFileFilter(String extension);
}

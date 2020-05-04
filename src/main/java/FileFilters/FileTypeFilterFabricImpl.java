package FileFilters;


import java.io.File;
import java.io.FileFilter;

public class FileTypeFilterFabricImpl implements FileTypeFilterFabric {

    public FileFilter makeFileFilter(final String extension) {
        return new FileFilter() {
            public boolean accept(File pathname) {
                if(pathname.isDirectory()) return true;
                return pathname.getName().toLowerCase().endsWith(extension);
            }
        };
    }
}

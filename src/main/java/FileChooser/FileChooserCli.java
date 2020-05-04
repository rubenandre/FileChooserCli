package FileChooser;

import FileFilters.FileTypeFilterFabric;
import FileFilters.FileTypeFilterFabricImpl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;

public class FileChooserCli {
    private final File basePath;
    private File currentPath;
    private FileFilter fileFilter;
    private Scanner scanner;
    private List<File> actualPathFiles;

    public FileChooserCli(final String basePath) throws FileNotFoundException {
        this.basePath = new File(basePath).getAbsoluteFile();
        this.currentPath = this.basePath;
        this.scanner = new Scanner(System.in);
        if (!this.basePath.exists() || !this.currentPath.exists()) {
            throw new FileNotFoundException("File does not exist");
        }
    }

    public void useFilter(String filter) {
        FileTypeFilterFabric fileTypeFilterFabric = new FileTypeFilterFabricImpl();
        fileFilter = fileTypeFilterFabric.makeFileFilter(filter);
    }

    public void show() {
        int selectedOption = -1;
        File selectedFile = currentPath.getAbsoluteFile();
        while (!(selectedFile.isFile())) {
            listFilesActualPath();
            System.out.print("Select a file: ");
            while (selectedOption == -1){
                try {
                    selectedOption = scanner.nextInt();
                    selectedFile = actualPathFiles.get(selectedOption);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    selectedOption = -1;
                }
            }
            selectedOption = -1;
            walk(selectedFile);
        }
        System.out.println(selectedFile);
    }

    private void walk(File selectedFile) {
        if (selectedFile.isDirectory()) {
            if (selectedFile.getName().equals("..")) {
                currentPath = currentPath.getParentFile();
            }
            else {
                currentPath = selectedFile.getAbsoluteFile();
            }
        }
    }

    private void listFilesActualPath() {
        actualPathFiles = findFilesActualPath();
        actualPathFiles.add(0, new File(".."));
        System.out.printf("Files (%s)%n", currentPath.getAbsolutePath());
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < actualPathFiles.size(); i++) {
            System.out.printf("%d) %s%n", i, actualPathFiles.get(i));
        }
    }

    private List<File> findFilesActualPath() {
        if (fileFilter == null) return new ArrayList<>(Arrays.asList(Objects.requireNonNull(currentPath.listFiles())));
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(currentPath.listFiles(fileFilter))));
    }
}

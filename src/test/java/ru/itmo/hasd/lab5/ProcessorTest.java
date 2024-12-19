package ru.itmo.hasd.lab5;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.regex.MatchResult;

class ProcessorTest {

    private static final String SMALL_TXT_PATH =
            "/Users/a.sashina/IdeaProjects/saving-compression-algorithms/data/small.txt";
    private static final String MEDIUM_TXT_PATH =
            "/Users/a.sashina/IdeaProjects/saving-compression-algorithms/data/medium.txt";
    private static final String BIG_TXT_PATH =
            "/Users/a.sashina/IdeaProjects/saving-compression-algorithms/data/big.txt";

    private final Processor processor = new Processor();

    @TempDir
    private File file;

    @Test
    @SneakyThrows
    void smallTextTest() {
        System.out.println("--- Обработка маленького файла ---");
        var file = new File(SMALL_TXT_PATH);

        var loudsTree = processor.createLoudsTree(file.getAbsolutePath());
        System.out.printf("Объем памяти исходного текста: %s Кб\n", file.length() / 1024);
        System.out.printf(
                "Объем памяти LOUDS структуры: %s Кб\n",
                (loudsTree.getLBS().size() / 8 + loudsTree.getLabels().size() + loudsTree.getIsLeaf().size() / 8) / 1024);

        var fileLzf = new File(this.file, "lzf.txt");
        processor.createLzfFile(SMALL_TXT_PATH, fileLzf.getPath());
        System.out.printf("Объем памяти текста с сжатием LZF: %s Кб\n", fileLzf.length() / 1024);

        var start = System.currentTimeMillis();
        var resultLoads = loudsTree.match("vein");
        var timeLoads = System.currentTimeMillis() - start;

        var fileIndex = new File(this.file, "index0.txt");
        var scanner = processor.createFileIndex(SMALL_TXT_PATH, fileIndex.getPath());
        start = System.currentTimeMillis();
        scanner.findAll("vein*")
                .map(MatchResult::group)
                .toList();
        var timeScanner = System.currentTimeMillis() - start;
        System.out.printf("""
                        Поиск содержания паттерна "vein"
                        Результат: :%s
                        [LOUDS] Время поиска: %s мс
                        [Scanner] Время поиска: %s мс
                        """,
                resultLoads.name(),
                timeLoads,
                timeScanner);
    }

    @Test
    @SneakyThrows
    void mediumTextTest() {
        System.out.println("--- Обработка среднего файла ---");
        var file = new File(MEDIUM_TXT_PATH);

        var loudsTree = processor.createLoudsTree(file.getAbsolutePath());
        System.out.printf("Объем памяти исходного текста: %s Кб\n", file.length() / 1024);
        System.out.printf(
                "Объем памяти LOUDS структуры: %s Кб\n",
                (loudsTree.getLBS().size() / 8 + loudsTree.getLabels().size() + loudsTree.getIsLeaf().size() / 8) / 1024);

        var fileLzf = new File(this.file, "lzf.txt");
        var f = processor.createLzfFile(MEDIUM_TXT_PATH, fileLzf.getAbsolutePath());
        System.out.printf("Объем памяти текста с сжатием LZF: %s Кб\n", f.length() / 1024);

        var start = System.currentTimeMillis();
        var resultLoads = loudsTree.match("vein");
        var timeLoads = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        var fileIndex0 = new File(this.file, "index0.txt");
        var scanner = processor.createFileIndex(MEDIUM_TXT_PATH, fileIndex0.getPath());
        scanner.findAll("vein*")
                .map(MatchResult::group)
                .toList();
        var timeScanner = System.currentTimeMillis() - start;
        System.out.printf("""
                        Поиск содержания паттерна "vein"
                        Результат: :%s
                        [LOUDS] Время поиска: %s мс
                        [Scanner] Время поиска: %s мс
                        """,
                resultLoads,
                timeLoads,
                timeScanner);
    }

    @Test
    @SneakyThrows
    void bigTextTest() {
        System.out.println("--- Обработка большого файла ---");
        var file = new File(BIG_TXT_PATH);

        var loudsTree = processor.createLoudsTree(file.getAbsolutePath());
        System.out.printf("Объем памяти исходного текста: %s Кб\n", file.length() / 1024);
        System.out.printf(
                "Объем памяти LOUDS структуры: %s Кб\n",
                (loudsTree.getLBS().size() / 8 + loudsTree.getLabels().size() + loudsTree.getIsLeaf().size() / 8) / 1024);

        var fileLzf = new File(this.file, "lzf.txt");
        processor.createLzfFile(BIG_TXT_PATH, fileLzf.getAbsolutePath());
        System.out.printf("Объем памяти текста с сжатием LZF: %s Кб\n", fileLzf.length() / 1024);

        var start = System.currentTimeMillis();
        var resultLoads = loudsTree.match("vein");
        var timeLoads = System.currentTimeMillis() - start;

        var fileIndex = new File(this.file, "index0.txt");
        var scanner = processor.createFileIndex(BIG_TXT_PATH, fileIndex.getPath());
        start = System.currentTimeMillis();
        scanner.findAll("vein*")
                .map(MatchResult::group)
                .toList();
        var timeScanner = System.currentTimeMillis() - start;
        System.out.printf("""
                        Поиск содержания паттерна "vein"
                        Результат: :%s
                        [LOUDS] Время поиска: %s мс
                        [Scanner] Время поиска: %s мс
                        """,
                resultLoads,
                timeLoads,
                timeScanner);
    }

}

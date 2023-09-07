package io.hogenboom.familyfoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("io.hogenboom.*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    public static void main(String[] args) {
//        PdfReader reader = new PdfReader();
//        reader.readPdf("C:\\Users\\StephanHogenboom\\Downloads\\2023_08_Wijziging_lening.pdf");
//    }
//
//    private static void sortFiles() {
//        var sorterService = new DateNameSortingService();
//        sorterService.sortAllInUnsortedDirectoryAndCopyToTargetDirectory(
//                Paths.get("C:\\Users\\StephanHogenboom\\Downloads\\unsorted"),
//                Paths.get("C:\\Users\\StephanHogenboom\\Downloads\\sorted"));
//    }
}

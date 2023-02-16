import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AntiCheat {

    public static void main(String[] args) throws IOException {
        String line143 = Files.readAllLines(Paths.get("src/org/openjfx/controller/GameController.java")).get(143);
        String line149 = Files.readAllLines(Paths.get("src/org/openjfx/controller/GameController.java")).get(149);
        String line158 = Files.readAllLines(Paths.get("src/org/openjfx/controller/GameController.java")).get(158);
        String line161 = Files.readAllLines(Paths.get("src/org/openjfx/controller/GameController.java")).get(161);
        String line152 = Files.readAllLines(Paths.get("src/org/openjfx/view/PawnView.java")).get(152);
        String line153 = Files.readAllLines(Paths.get("src/org/openjfx/view/PawnView.java")).get(153);

        System.out.println(line143);
        System.out.println(line149);
        System.out.println(line158);
        System.out.println(line161);
        System.out.println(line152);
        System.out.println(line153);


        line143 = line143.substring(2);
        line149 = line149.substring(2);
        line158 = line158.substring(2);
        line161 = line161.substring(2);
        line152 = line152.substring(2);
        line153 = line153.substring(2);

        System.out.println(line143);
        System.out.println(line149);
        System.out.println(line158);
        System.out.println(line161);
        System.out.println(line152);
        System.out.println(line153);

        Path path1 = Paths.get("src/org/openjfx/controller/GameController.java");
        Path path2 = Paths.get("src/org/openjfx/view/PawnView.java");
        List<String> lines1 = Files.readAllLines(path1, StandardCharsets.UTF_8);
        List<String> lines2 = Files.readAllLines(path2, StandardCharsets.UTF_8);
        lines1.set(143, line143);
        lines1.set(149, line149);
        lines1.set(158, line158);
        lines1.set(161, line161);
        lines2.set(152, line152);
        lines2.set(153, line153);
        Files.write(path1, lines1, StandardCharsets.UTF_8);
        Files.write(path2, lines2, StandardCharsets.UTF_8);
    }
}

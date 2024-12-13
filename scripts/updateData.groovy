import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Consumer

final Path entriesPath = Paths.get("entries/")

if (!Files.exists(entriesPath)) {
    Files.createDirectories(entriesPath)
}

final ArrayList<String> discovered = new ArrayList<>()

Files.walk(entriesPath).forEach{it -> {
    if (it != entriesPath) {
        discovered.add(it.getFileName() as String)
    }
}}

discovered.sort {(it1, it2) -> {
    return (it1 as String).toLowerCase() <=> (it2 as String).toLowerCase()
}}

println "Discovered ${discovered.size()} files"

final StringBuilder output = new StringBuilder()

discovered.forEach {s -> output.append(s).append("\n")}

final Path dataPath = Paths.get("data")

if (!Files.exists(dataPath)) {
    Files.createDirectories(dataPath)
}

final Path workingFile = Paths.get(String.format("%s/available", dataPath))

try (final FileWriter writer = new FileWriter(workingFile.toFile())) {
    writer.write(output.toString())
}
public static Map<String, String> runCode(String code, String language) {
    Map<String, String> result = new HashMap<>();
    String ext = switch (language) {
        case "python" -> ".py";
        case "bash" -> ".sh";
        case "js" -> ".js";
        default -> ".txt";
    };

    String filename = "/tmp/" + UUID.randomUUID() + ext;
    String[] command = switch (language) {
        case "python" -> new String[]{"python3", filename};
        case "bash" -> new String[]{"bash", filename};
        case "js" -> new String[]{"node", filename};
        default -> null;
    };

    if (command == null) {
        result.put("error", "Unsupported language");
        return result;
    }

    try {
        Files.write(Paths.get(filename), code.getBytes());
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            output.append(line).append("\n");

        int exitCode = process.waitFor();
        result.put("output", output.toString().trim());
        result.put("error", exitCode == 0 ? "" : "Exited with code: " + exitCode);

    } catch (Exception e) {
        result.put("error", e.getMessage());
    } finally {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException ignored) {}
    }

    return result;
}

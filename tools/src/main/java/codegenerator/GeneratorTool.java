package codegenerator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratorTool {

    public static void main(String[] args) {

        String file = args[0];
        String outputFile = args[1];
        String implementationClass = args[2];
        String packageName = args[3];

        parseFile(file, implementationClass, outputFile, packageName);

    }

    public static void parseFile(String file, String implementationClass, String outputFile, String packageName) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {

            Object object = parser.parse(reader);

            JSONArray jsonList = (JSONArray) object;

            JSONObject jsonObject = (JSONObject) jsonList.get(0);

            for (Object objName : jsonObject.keySet()) {
                String className = (String) objName;
                File livingDir = new File(outputFile + "/" + packageName);
                livingDir.mkdirs();
                try (FileWriter writer = new FileWriter(livingDir.getPath() + "/" + className.toString() + ".java")) {

                    writer.append("package ").append(packageName.replace('/', '.')).append(";").append("\n\n");
                    writer.append("import com.fasterxml.jackson.annotation.JsonProperty;").append("\n\n");
                    writer.append("import java.util.*;\n;").append("\n\n");
                    writer.append("import java.sql.Date;\n;").append("\n\n");

                    writer.append("public class ").append(className);

                    if (!implementationClass.isEmpty()) {
                        writer.append(" implements ").append(implementationClass);
                    }

                    writer.append(" {").append("\n");

                    JSONObject properties = (JSONObject) jsonObject.get(className);

                    createProperties(writer, properties);
                    createSetters(className, writer, properties);
                    createGetters(writer, properties);
                    createToString(writer, properties, className);
                    createEquals(writer, properties, className);
                    writer.append("}");
                }

            }


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createProperties(FileWriter writer, JSONObject properties) throws IOException {
        for (Object objProperty : properties.keySet()) {
            String typeUnsplit = (String) properties.get(objProperty);
            String[] typeSplit = typeUnsplit.split(" ");
            String type = typeSplit[0];

            writer.append("    @JsonProperty(\"").append(objProperty.toString()).append("\")").append("\n");
            writer.append("    private ");

            if (typeSplit.length > 1 && typeSplit[1].equals("[]")) {
                writer.append("List<").append(type).append(">");
            } else {
                writer.append(type);

            }
            writer.append(" ").append(objProperty.toString()).append(";").append("\n\n");
        }
    }

    private static void createSetters(String className, FileWriter writer, JSONObject properties) throws IOException {
        for (Object objProperty : properties.keySet()) {
            String typeUnsplit = (String) properties.get(objProperty);
            String[] typeSplit = typeUnsplit.split(" ");
            String type = typeSplit[0];


            writer.append("    public ")
                    .append(className)
                    .append(" with").append(objProperty.toString().substring(0, 1).toUpperCase()).append(objProperty.toString().substring(1))
                    .append("(final ");

            if (typeSplit.length > 1 && typeSplit[1].equals("[]")) {
                writer.append("List<").append(type).append(">");
            } else {
                writer.append(type);

            }
            writer.append(" ")
                    .append(objProperty.toString())
                    .append(") {")
                    .append("\n");
            writer.append("        this.").append(objProperty.toString()).append(" = ").append(objProperty.toString()).append(";").append("\n");
            writer.append("        return this;").append("\n");
            writer.append("    }\n\n");
        }
    }

    private static void createGetters(FileWriter writer, JSONObject properties) throws IOException {
        for (Object objProperty : properties.keySet()) {
            String typeUnsplit = (String) properties.get(objProperty);
            String[] typeSplit = typeUnsplit.split(" ");
            String type = typeSplit[0];

            writer.append("    public ");
            if (typeSplit.length > 1 && typeSplit[1].equals("[]")) {
                writer.append("List<").append(type).append(">");
            } else {
                writer.append(type);

            }

            writer.append(" ")
                    .append(objProperty.toString())
                    .append("() { ")
                    .append("\n");
            writer.append("        return ").append(objProperty.toString()).append(";").append("\n");
            writer.append("    }\n\n");
        }
    }

    private static void createToString(FileWriter writer, JSONObject properties, String className) throws IOException {

        writer.append("    @Override").append("\n");
        writer.append("    public String toString() {").append("\n");
        writer.append("        return  ").append("\"").append(className).append("{\" +\n");
        int lastPropertyIndex = 0;
        for (Object objProperty : properties.keySet()) {
            lastPropertyIndex++;

            if(lastPropertyIndex == properties.keySet().size())
            {
                writer.append("            \"").append(objProperty.toString()).append("=\'\" + ").append(objProperty.toString()).append(" + \'\\\'\' +\n");
            }
            else
            {
                writer.append("            \"").append(objProperty.toString()).append("=\'\" + ").append(objProperty.toString()).append(" + \'\\\'\' + \",\" +\n");
            }

        }
        writer.append("            \'}\';\n");
        writer.append("    };\n");
    }

    private static void createEquals(FileWriter writer, JSONObject properties, String className) throws IOException {

        writer.append("    @Override").append("\n");
        writer.append("    public boolean equals(Object o) {").append("\n");
        writer.append("    if (this == o) return true;\n");
        writer.append("    if (o == null || getClass() != o.getClass()) return false;\n");
        if(properties.keySet().size() == 1)
        {
            writer.append("        return true;\n");
        }
        else
        {
            writer.append("        ").append(className).append(" that = (").append(className).append(") o;\n");
            for (Object objProperty : properties.keySet()) {

                if(!objProperty.toString().equals("id"))
                {
                    writer.append("        if(");
                    writer.append("!Objects.equals(").append(objProperty.toString()).append(", that.").append(objProperty.toString()).append(")");
                    writer.append(")\n");
                    writer.append("{ return false;}\n");
                }
            }
            writer.append("        return true;\n");

        }
        writer.append("    };\n");
    }
}

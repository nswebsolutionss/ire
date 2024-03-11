package com.generator;


import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.*;

import javax.inject.Inject;
import java.io.File;

import static java.util.Arrays.asList;

@CacheableTask
public class GradleGeneratorTask extends JavaExec {
    private static final String GENERATOR_TOOL = "codegenerator.GeneratorTool";

    public static final String OUTPUT_DIR = "src/main/java";
    private File schemaFile;
    private File outputDir;
    private String packageName;
    public String implementationName = "";

    @Inject
    public GradleGeneratorTask()
    {
        getMainClass().set(GENERATOR_TOOL);
        setDefaultOutputDir();
        setDefaultClasspath();
    }

    private void setDefaultClasspath() {
        final Configuration configuration = getProject().getConfigurations().findByName("generateInternal");
        getProject().getConfigurations().forEach(
                files -> System.out.println(files.getName())
        );
        System.out.println(configuration);
        if(configuration != null)
        {
            setClasspath(configuration);
        }
    }

    private void setDefaultOutputDir() {
        setOutputDir(new File(OUTPUT_DIR));
    }

    public void setImplementationName(String implementationName)
    {
        this.implementationName = implementationName;
    }

    @Input
    public String getPackageName()
    {
        return this.packageName;
    }


    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    @Input
    public String getImplementationName()
    {
        return this.implementationName;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    @OutputDirectory
    public File getOutputDir()
    {
        return this.outputDir;
    }

    @InputFile
    @PathSensitive(PathSensitivity.NAME_ONLY)
    public File getSchemaFile()
    {
        return schemaFile;
    }

    public void setSchemaFile(File schemaFile)
    {
        this.schemaFile = schemaFile;
    }

    @Override
    @TaskAction
    public void exec()
    {
        setArgsForExecution();
        super.exec();
    }

    private void setArgsForExecution() {
        setArgs(asList(
                schemaFile.getPath(),
                outputDir.getPath(),
                implementationName,
                packageName
        ));
    }


}

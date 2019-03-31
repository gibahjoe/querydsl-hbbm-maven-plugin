package com.devappliance.qdslhbmplugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.querydsl.jpa.codegen.HibernateDomainExporter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mojo(name = "qdsl", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class Plugin extends AbstractMojo {

    @Parameter(property = "targetFolder", defaultValue = "target/generated-sources/java")
    private String targetFolder;

    @Parameter(property = "hibernateConfigFile")
    private File hibernateConfigFile;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        getLog().info("***********************************************************");
        getLog().info("***                                                     ***");
        getLog().info("***                Generating QDSL files                ***");
        getLog().info("***                                                     ***");
        getLog().info("***********************************************************");
        try {
            File targetFolder = new File(this.targetFolder);

            try {
                Set<URL> urls = new HashSet<>();
                List<String> elements = project.getCompileClasspathElements();
                //getRuntimeClasspathElements()
                //getCompileClasspathElements()
                //getSystemClasspathElements()
                for (String element : elements) {
                    urls.add(new File(element).toURI().toURL());
                }

                ClassLoader contextClassLoader = URLClassLoader.newInstance(
                        urls.toArray(new URL[0]),
                        Thread.currentThread().getContextClassLoader());

                Thread.currentThread().setContextClassLoader(contextClassLoader);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Configuration configuration;
            if (hibernateConfigFile == null || !hibernateConfigFile.exists()) {
                configuration = new Configuration().configure();
            } else {
                configuration = new Configuration();
                configuration.configure(hibernateConfigFile);
            }

            HibernateDomainExporter exporter = new HibernateDomainExporter("Q", targetFolder, configuration);

            exporter.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        project.addCompileSourceRoot(this.targetFolder);
    }

}

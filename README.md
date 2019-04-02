# querydsl-hbbm-maven-plugin
Plugin for QueryDsl to generate Model files from Hibernate hbm.xml files

# Simple usage

```
            <plugin>
                <groupId>com.devappliance.qdslhbmplugin</groupId>
                <artifactId>querydsl-hbm-maven-plugin</artifactId>
                <version>1.0</version>
                <dependencies>
                    <dependency>
                        <groupId>org.hibernate</groupId>
                        <artifactId>hibernate-core</artifactId>
                        <version>4.3.8.Final</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>qdsl</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <hibernateConfigFile>${basedir}/path/to/mapping/file</hibernateConfigFile>
                    <targetFolder>target/generated-sources/java/queryDsl</targetFolder>
                </configuration>
            </plugin>
 ```

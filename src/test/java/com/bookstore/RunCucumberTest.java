package com.bookstore;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore // Enabled only when running profile: -Pcucumber
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/bookstore/features",
        glue = "com.bookstore.steps",
        plugin = {"pretty","summary"},
        tags = "not @wip"
)
public class RunCucumberTest {

}

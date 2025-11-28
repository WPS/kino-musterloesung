package de.wps.ddd.kino.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.wps.ddd.kino", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    ArchRule dddRules = JMoleculesDddRules.all();

    @ArchTest
    ArchRule hexagonalRules = JMoleculesArchitectureRules.ensureHexagonal();

    @ArchTest
    ArchRule noPackageCycles = SlicesRuleDefinition.slices().matching("(**)").should().beFreeOfCycles();
}

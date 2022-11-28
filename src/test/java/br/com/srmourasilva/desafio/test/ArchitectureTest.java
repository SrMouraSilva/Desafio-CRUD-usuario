package br.com.srmourasilva.desafio.test;

import br.com.srmourasilva.desafio.DesafioApplication;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.GeneralCodingRules;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.conditions.ArchConditions.haveSimpleNameEndingWith;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


public class ArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    public static void setUp() {
        classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackagesOf(DesafioApplication.class);
    }

    @Test
    public void mustNotDefineSystem() {
        ArchRuleDefinition.noClasses().should(GeneralCodingRules.ACCESS_STANDARD_STREAMS).check(classes);
    }

    @Test
    public void mustNotUseSystem() {
        GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    }

    @Test
    public void mustNotThrowsGenericExceptions() {
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    }

    @Test
    public void checkEntities() {
        ArchRuleDefinition.classes()
            .that()
            .areAnnotatedWith(Document.class)
            .should()
            .resideInAnyPackage("br.com.srmourasilva.desafio.model")
            .check(classes);
    }

    @Disabled("Fixme!")
    @Test
    public void checkRepositories() {
        ArchRuleDefinition.classes()
            .that()
            .areAnnotatedWith(Repository.class)
            .should()
            .resideInAnyPackage("br.com.srmourasilva.desafio.repository")
            .andShould(
                haveSimpleNameEndingWith("Repository")
                    .or(haveSimpleNameEndingWith("RepositoryImpl"))
            )
            .check(classes);
    }

    @Disabled("Fixme!")
    @Test
    public void checkDTOs() {
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("br.com.srmourasilva.desafio.dto")
            .should()
            .haveSimpleNameEndingWith("DTO")
            .check(classes);
    }

    @Test
    public void checkControllers() {
        ArchRuleDefinition.classes()
            .that()
            .areAnnotatedWith(RestController.class)
            .should()
            .resideInAnyPackage("br.com.srmourasilva.desafio.controller")
            .andShould()
            .beAnnotatedWith(RequestMapping.class)
            .andShould()
            .beAnnotatedWith(Tag.class)
            .check(classes);
    }

    @Test
    public void checkLayers() {
        final String pacoteController = "br.com.srmourasilva.desafio.controller";
        final String pacoteUseCase = "br.com.srmourasilva.desafio.usecase..";
        final String pacoteRepository = "br.com.srmourasilva.desafio.repository";
        final String pacoteSecurity = "br.com.srmourasilva.desafio.config.security";

        // FIXME - Excessive
        layeredArchitecture().consideringAllDependencies()
            .layer("Controller").definedBy(pacoteController)
            .layer("UseCase").definedBy(pacoteUseCase)
            .layer("Repository").definedBy(pacoteRepository)
            .layer("Security").definedBy(pacoteSecurity)

            .whereLayer("Controller")
            .mayNotBeAccessedByAnyLayer()

            .whereLayer("UseCase")
            .mayOnlyBeAccessedByLayers("Security", "Controller", "UseCase")


            .whereLayer("Repository")
            .mayOnlyBeAccessedByLayers("UseCase", "Security", "Controller")

            .whereLayer("Security")
            .mayOnlyBeAccessedByLayers("Security", "UseCase", "Controller")

            .check(classes);

        SlicesRuleDefinition.slices()
            .matching("br.com.srmourasilva.(*)..")
            .should()
            .beFreeOfCycles()
            .check(classes);
    }
}

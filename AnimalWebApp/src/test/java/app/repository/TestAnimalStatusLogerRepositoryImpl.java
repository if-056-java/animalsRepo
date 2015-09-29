package app.repository;

import app.JNDIConfigurationForTests;
import com.animals.app.domain.AnimalStatusLoger;
import com.animals.app.repository.Impl.AnimalStatusLogerRepositoryImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalStatusLogerRepositoryImpl extends JNDIConfigurationForTests {

    private static AnimalStatusLogerRepositoryImpl animalStatusLogerRepositoryImpl;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        animalStatusLogerRepositoryImpl = new AnimalStatusLogerRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        animalStatusLogerRepositoryImpl = null;
    }

    @Test
    public void test00GetAll(){
        List<AnimalStatusLoger> expected = new AnimalStatusLogerRepositoryImpl().getAll();

        assertNotNull(expected);
    }

    @Test
    public void test01GetById() {
        AnimalStatusLoger expected = animalStatusLogerRepositoryImpl.getById(animalStatusLogerRepositoryImpl.getAll().get(0).getId());

        assertNotNull(expected);
    }

    @Test
    public void test02GetStatusByAnimalId(){
        List<AnimalStatusLoger> expected = new AnimalStatusLogerRepositoryImpl().getAnimalStatusesByAnimalId(animalStatusLogerRepositoryImpl.getAll().get(0).getId());

        assertNotNull(expected);
    }
}

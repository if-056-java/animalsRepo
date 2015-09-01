package app.repository;

import com.animals.app.domain.AnimalService;
import com.animals.app.repository.Impl.AnimalServiceRepositoryImpl;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class TestAnimalServiceRepositoryImpl {
    private static AnimalServiceRepositoryImpl animalServiceRepositoryImpl;

    @BeforeClass
    public static void runBeforeClass() {
        animalServiceRepositoryImpl = new AnimalServiceRepositoryImpl();
    }

    @AfterClass
    public static void runAfterClass() {
        animalServiceRepositoryImpl = null;
    }

    @Test
    public void test01GetAll() {
        List<AnimalService> expected = animalServiceRepositoryImpl.getAll();

        assertNotNull(expected);
        assertNotEquals(expected.size(), 0);
    }

    @Test
    public void test02GetById() {
        long testId = animalServiceRepositoryImpl.getAll().get(0).getId();

        assertNotEquals(testId, 0);

        AnimalService expected = animalServiceRepositoryImpl.getById(testId);

        assertNotNull(expected);
    }

    @Test
    public void test03GetById() {
        AnimalService expected = animalServiceRepositoryImpl.getById(-1);

        assertNull(expected);
    }
}

package app.repository;

import com.animals.app.domain.AnimalBreed;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Rostyslav.Viner on 06.08.2015.
 */
public class TestAnimalBreedRepositoryImpl {
    private static AnimalBreedRepositoryImpl animalBreedRepositoryImpl;

    @Before
    public void runBeforeClass() {
        animalBreedRepositoryImpl = new AnimalBreedRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        animalBreedRepositoryImpl = null;
    }

    @Test
    public void testGetAll() {
        List<AnimalBreed> list = animalBreedRepositoryImpl.getAll();

        assertNotNull(list);
    }

    @Test
    public void testGetById() {
        AnimalBreed expected = animalBreedRepositoryImpl.getById(1);

        assertNotNull(expected);
    }

    @Test
    public void testGetByTypeId() {
        List<AnimalBreed> expected = animalBreedRepositoryImpl.getByTypeId(1);

        assertNotNull(expected);
    }
}

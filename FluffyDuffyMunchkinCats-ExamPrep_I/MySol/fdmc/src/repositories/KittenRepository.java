package repositories;

import entities.Kitten;

import java.util.List;

/**
 * Created by George-Lenovo on 02/23/2018.
 */
public class KittenRepository extends BaseRepository implements Repository<Kitten> {
    @Override
    public void create(Kitten kitten) {
        super.execute(actionResult -> {
            super.entityManager.persist(kitten);
            /*String addKittenQuery = String.format("insert into kittens(name,age,breed)\n" +
                            "values ('%s',%d, '%s')",
                    kitten.getName(),
                    kitten.getAge(),
                    BreedType.getSimpleValue(kitten.getBreed()));
            super.entityManager.createNativeQuery(addKittenQuery);*/
        });
    }

    @Override
    public Kitten findById(String id) {
        Kitten kitten = (Kitten) super.execute(actionResult -> {
            String findByIdQuery = String.format("SELECT * FROM kittens k WHERE k.id = '%s'", id);
            Object queryResult = super.getQueryResult(findByIdQuery);
            actionResult.setResult(queryResult);
        }).getResult();

        return kitten;
    }

    @Override
    public Kitten findByName(String name) {
        Kitten kitten = (Kitten) super.execute(actionResult -> {
            String findByNameQuery = String.format("SELECT * FROM kittens k WHERE k.name = '%s'", name);

            Object queryResult = super.getQueryResult(findByNameQuery);

            actionResult.setResult(queryResult);
        }).getResult();

        return kitten;
    }

    @Override
    public Kitten[] findAll() {
        List<Kitten> kittenList = (List<Kitten>) super.execute(actionResult -> {
            String findAllKittiesQuery = "SELECT * FROM kittens as k";

            Object queryResult = super.getQueryResult(findAllKittiesQuery);

            actionResult.setResult(queryResult);
        }).getResult();

        return kittenList.toArray(new Kitten[kittenList.size()]);
    }
}

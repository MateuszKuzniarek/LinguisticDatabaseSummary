package logic.membership;

import data.DatabaseRepository;

public class RelativeQuantifier extends Quantifier {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    @Override
    public double getValue(double r) {
        int entityCount = databaseRepository.getPlayerCount();
        return membershipFunction.calculateMembership(r/entityCount);
    }
}

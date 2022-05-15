package SetsModel;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Function {

    @Getter FuzzyOperationsType operationType;
    @Getter Function function;
    @Getter MembershipFunction membershipFunction;

    public Function(FuzzyOperationsType operationType, MembershipFunction functionType, Function otherFunction) {
        this.operationType = operationType;


        membershipFunction = functionType;
        if (otherFunction != null)
            function = otherFunction;
    }

    public void addFunction(Function otherFunction, FuzzyOperationsType operationType) {

        function = otherFunction;
        this.operationType = operationType;
    }

    public Double calculateMembership(Double x) {

        if (operationType.equals(FuzzyOperationsType.None)) {

            return membershipFunction.calculateMembership(x);
        }
        else {

            switch (operationType){

                case Sum -> {

                    List<Double> options = new ArrayList<>();

                    options.add(membershipFunction.calculateMembership(x));
                    options.add(function.calculateMembership(x));

                    return options.stream().max(Double::compareTo).orElse(0.0);

                }
                case Product -> {

                    List<Double> options = new ArrayList<>();

                    options.add(membershipFunction.calculateMembership(x));
                    options.add(function.calculateMembership(x));

                    return options.stream().min(Double::compareTo).orElse(0.0);

                }
            }
        }

        return 0.0;
    }

}

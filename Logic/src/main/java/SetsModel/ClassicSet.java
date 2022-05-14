package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ClassicSet implements SetsOperations<ClassicSet> {

    @Getter private List<Double> elements;

    @Getter private Space space;

    @Getter @Setter private boolean complement;

    @Override
    public ClassicSet sum(ClassicSet s1, ClassicSet s2) throws CloneNotSupportedException {

        Set<Double> elements = new HashSet<>();

        if (s1.isComplement() == s2.isComplement() && !s1.isComplement()) {

            elements.addAll(s1.getElements());
            elements.addAll(s2.getElements());

            return new ClassicSet(elements.stream().toList(), s1.getSpace(), false);
        }
        else if (s1.isComplement() == s2.isComplement() && isComplement()) {

            for (int i = 0; i < s1.getElements().size(); i++){

                if (s2.getElements().contains(s1.getElements().get(i))){
                    elements.add(s1.getElements().get(i));
                }
            }

            return new ClassicSet(elements.stream().toList(), s1.getSpace(), true);
        }
        else {

            if (s2.isComplement()){

                return cloneClassicSet(s2, s1);
            }
            else {

                return cloneClassicSet(s1, s2);
            }
        }

    }

    private ClassicSet cloneClassicSet(ClassicSet s1, ClassicSet s2) throws CloneNotSupportedException {
        s1 = (ClassicSet) s1.clone();

        for (int i = 0; i < s2.getElements().size(); i++){

            if (s1.contains(s2.getElements().get(i))){

                s1.removeElement(s2.getElements().get(i));
            }
        }

        return new ClassicSet(s1.getElements(), s1.getSpace(), true);
    }

    @Override
    public ClassicSet product(ClassicSet s1, ClassicSet s2) {
        return null;
    }

    public boolean contains(Double element) {

        if (!isComplement()){

            return getSpace().contains(element) && getElements().contains(element);
        }
        else {

            if (!getElements().contains(element) && getSpace().contains(element))
            {
                return true;
            }
            else return false;
        }
    }

   public void removeElement(Double element){

        getElements().remove(element);
   }
}

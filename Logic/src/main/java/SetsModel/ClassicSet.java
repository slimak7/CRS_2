package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@AllArgsConstructor
public class ClassicSet implements SetsOperations<ClassicSet> {

    @Getter private List<Double> elements;

    @Getter private Space space;

    @Getter @Setter private boolean complement;

    public ClassicSet(Space space) {
        this.space = space;
        complement = false;
        elements = new ArrayList<>();
    }

    public ClassicSet(Space space, boolean complement) {
        this.space = space;
        this.complement = complement;
        elements = new ArrayList<>();
    }

    @Override
    public ClassicSet sum(ClassicSet s2)  {


        ClassicSet s1 = this;
        Set<Double> elements = new HashSet<>();

        if (s1.isComplement() == s2.isComplement() && !s1.isComplement()) {

            elements.addAll(s1.getElements());
            elements.addAll(s2.getElements());

            return new ClassicSet(elements.stream().toList(), s1.getSpace(), false);
        }
        else if (s1.isComplement() == s2.isComplement() && s1.isComplement()) {

            for (int i = 0; i < s1.getElements().size(); i++){

                if (s2.getElements().contains(s1.getElements().get(i))){
                    elements.add(s1.getElements().get(i));
                }
            }

            return new ClassicSet(elements.stream().toList(), s1.getSpace(), true);
        }
        else {

            if (s2.isComplement()){

                return sumSetAndComplement(s2, s1);
            }
            else {

                return sumSetAndComplement(s1, s2);
            }
        }

    }

    private ClassicSet sumSetAndComplement(ClassicSet s1, ClassicSet s2) {

        List<Double> elements = new ArrayList<>();
        elements.addAll(s1.getElements());

        for (int i = 0; i < elements.size(); i++){

            if (s2.contains(elements.get(i))){

               elements.remove(elements.get(i));
            }
        }


        return new ClassicSet(elements, s1.getSpace(), true);
    }

    public  ClassicSet productSetsAndComplement(ClassicSet s1, ClassicSet s2) {

        List<Double> elements = new ArrayList<>();

        for (int i = 0; i < s1.getElements().size(); i++){

            if (s2.contains(s1.getElements().get(i))){

                elements.add(s1.getElements().get(i));
            }
        }

        return new ClassicSet(elements, s1.getSpace(), false);
    }

    @Override
    public ClassicSet product(ClassicSet s2) {



        ClassicSet s1 = this;
        Set<Double> elements = new HashSet<>();

        if (s1.isComplement() == s2.isComplement() && !s1.isComplement()) {

            for (int i = 0; i < s1.getElements().size(); i++){

                if (s2.getElements().contains(s1.getElements().get(i))){
                    elements.add(s1.getElements().get(i));
                }
            }

            return new ClassicSet(elements.stream().toList(), s1.getSpace(), false);
        }
        else if (s1.isComplement() == s2.isComplement() && s1.isComplement()) {

            elements.addAll(s1.getElements());
            elements.addAll(s2.getElements());


            return new ClassicSet(elements.stream().toList(), s1.getSpace(), true);
        }
        else {

            if (s2.isComplement()){

                return productSetsAndComplement(s1, s2);
            }
            else {

                return productSetsAndComplement(s2, s1);
            }
        }
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

   public void addElement(Double element) {
        getElements().add(element);
   }


}

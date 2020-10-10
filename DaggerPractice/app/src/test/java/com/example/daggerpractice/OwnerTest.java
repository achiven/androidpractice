package com.example.daggerpractice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class OwnerTest {

    @Test
    public void getPetName() throws Exception {
        Owner owner = new Owner(new Pet() {
            @Override
            public String getName() {
                return "애완 동물의 이름";
            }
        });


        assertThat(owner.getPetName(), is("애완 동물의 이름"));
//        System.out.println(owner.getPetName());
    }
}
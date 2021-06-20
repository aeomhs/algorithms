package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class SkillTree {

    public static void main(String[] args) {
        SkillTree test = new SkillTree();
        test.solution("CBD", new String[] { "BACDE", "CBADF", "AECB", "BDA" });
    }

    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        int[] levels = new int[26];
        int level = 1;

        for (int i = 0; i < skill.length(); i++) {
            levels[skill.charAt(i)-'A'] = level++;
        }

        System.out.println(Arrays.toString(levels));


        for (String skill_tree : skill_trees) {
            System.out.println(skill_tree);
            int nextLv = 1;

            boolean err = false;
            for (char sk : skill_tree.toCharArray()) {
                if (levels[sk-'A'] == 0){
                    continue;
                }
                else if (levels[sk-'A'] == nextLv) {
                    nextLv++;
                }
                else {
                    err = true;
                    break;
                }
                System.out.println(sk + " : " + (sk-'A')+ " : " +levels[sk-'A'] + " : " + err);
            }
            if (!err)
                answer++;
        }
        return answer;
    }
}

package sharedClasses;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Josep
 */
public class Pair<L, R> {
	public L first;
	public R second;
	
	/*
        Pre: -
	Post: Es crea una nova instància.
        */
	public Pair(L first, R second) {
            this.first = first;
            this.second = second;
        }
}


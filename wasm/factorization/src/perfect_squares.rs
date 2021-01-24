extern crate num_bigint;
extern crate num_traits;

use wasm_bindgen::prelude::*;
use std::fmt;
use num_bigint::BigUint;
use num_traits::identities::*;

const INPUT_BASE:u32 = 10;

pub struct PerfectSquares{
    smooth: u32,
    from: BigUint,
    to: BigUint,
    base: BigUint,
    pub squares: Vec<(BigUint, BigUint, Vec<bool>)>
}

fn bool_to_num(a: &bool) -> &str{
    match a {
        true => "1",
        _ => "0"
    }
}

impl fmt::Display for PerfectSquares {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        for (first, second, parity) in &self.squares {
            let num_parity :Vec<&str> = parity.iter().map(bool_to_num).collect();
            write!(f, "{};{};{}$\n", first, second, num_parity.join(" "));
        }
        Ok(())
    }
}

impl PerfectSquares{
    pub fn new(smooth: u32, from: BigUint, to: BigUint, base: BigUint) -> PerfectSquares{
        PerfectSquares{
            smooth,
            from,
            to,
            base,
            squares: Vec::new()
        }
    }

    pub fn get_primes_to(limit: u32) -> Vec<u32>{
        let mut sieve = vec![true; (limit+1) as usize];
        let mut res = vec![];
        for i in 2..=limit as usize{
            if sieve[i] == true{
                res.push(i as u32);
                let mut j = i * 2;
                while j <= limit as usize{
                    sieve[j] = false;
                    j += i;
                }
            }
        }
        res
    }

    pub fn is_divisible(mut number: BigUint, divisors: &[u32]) -> Option<Vec<bool>>{
        if number.is_zero() || number.is_one(){
            return None;
        }
        let mut res = vec![false; divisors.len()];
        for i in 0..divisors.len(){
            while (&number % divisors[i]).is_zero(){
                number /= divisors[i].clone();
                res[i] = !res[i];
            }
        }
        if number.is_one(){
            Some(res)
        } else {
            None
        }
    }

    pub fn compute(&mut self){
        let primes = PerfectSquares::get_primes_to(self.smooth.clone());
        let mut iter = self.from.clone();
        while iter <= self.to{
            let sq = &iter * &iter;
            let tmp = &sq % &self.base;
            match PerfectSquares::is_divisible(tmp.clone(), &primes){
                None => (),
                Some(res) => self.squares.push((iter.clone(), tmp, res)),
            }
            iter += 1_u64;
        }
    }
}

#[wasm_bindgen]
pub fn compute_perfect_squares(smooth: u32, from: &str, to: &str, base: &str) -> String{
    let from_num = match BigUint::parse_bytes(from.as_bytes(), INPUT_BASE){
        Some(num) => num,
        None => return "ERROR WRONG FROM STRING".to_string(),
    };
    let to_num = match BigUint::parse_bytes(to.as_bytes(), INPUT_BASE){
        Some(num) => num,
        None => return "ERROR WRONG TO STRING".to_string(),
    };
    let base_num = match BigUint::parse_bytes(base.as_bytes(), INPUT_BASE){
        Some(num) => num,
        None => return "ERROR WRONG BASE STRING".to_string(),
    };
    let mut ps = PerfectSquares::new(smooth, from_num, to_num, base_num);
    ps.compute();
    ps.to_string()
}

#[wasm_bindgen]
pub fn list_of_primes(limit: u32) -> Vec<u32>{
    PerfectSquares::get_primes_to(limit)
}

#[cfg(test)]
mod test{
    use crate::perfect_squares::compute_perfect_squares;

    #[test]
    fn test1(){
        println!("{}", compute_perfect_squares(17, "735", "900", "539873"));
    }
}
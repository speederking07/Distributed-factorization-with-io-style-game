extern crate num_bigint;
extern crate num_traits;

use wasm_bindgen::prelude::*;
use std::fmt;
use num_bigint::BigUint;
use num_traits::identities::*;
use crate::perfect_squares::PerfectSquares;
use std::convert::From;
use std::str::FromStr;
use std::num::ParseIntError;

const INPUT_BASE:u32 = 10;

pub struct LinearEquations {
    smooth: u32,
    base: BigUint,
    squares: Vec<(BigUint, BigUint, Vec<bool>)>,
}

struct Combination {
    max: u64,
    curr: u64,
}

impl Combination {
    pub fn new(size: u32) -> Combination {
        Combination {
            curr: 1,
            max: 2_u64.pow(size),
        }
    }
}

impl Iterator for Combination {
    type Item = Vec<bool>;
    fn next(&mut self) -> Option<Self::Item> {
        if self.max < self.curr {
            return None;
        }
        let mut result = vec![];
        let mut pointer = 1_u64;
        while pointer < self.max {
            if self.curr & pointer == pointer {
                result.push(true);
            } else {
                result.push(false);
            }
            pointer *= 2;
        }
        self.curr += 1;
        Some(result)
    }
}

impl LinearEquations {
    pub fn new(smooth: u32, base: BigUint, squares: Vec<(BigUint, BigUint, Vec<bool>)>) -> LinearEquations {
        LinearEquations {
            smooth,
            base,
            squares,
        }
    }

    fn add_row(A: &mut Vec<Vec<bool>>, from: usize, to: usize) {
        for i in 0..A[0].len() {
            A[to][i] ^= A[from][i];
        }
    }

    #[inline]
    fn swap_row(A: &mut Vec<Vec<bool>>, m: usize, n: usize) {
        A.as_mut_slice().swap(m, n);
    }

    pub fn gcd(mut a: BigUint, mut b: BigUint) -> BigUint {
        while !b.is_zero() {
            let t = &a % &b;
            a = b;
            b = t;
        }
        return a;
    }

    pub fn find_solution(&self) -> Result<(BigUint, BigUint), &str> {
        let primes = PerfectSquares::get_primes_to(self.smooth);
        let mut A = vec![vec![true; self.squares.len()]; primes.len()];
        for x in 0..self.squares.len() {
            let (_, _, fact) = &self.squares[x];
            for y in 0..primes.len() {
                if !fact[y] {
                    A[y][x] = false;
                }
            }
        }
        let mut x = 0_usize;
        let mut y = 0_usize;
        let mut free_variables = vec![];
        let mut none_trivial = vec![];
        let mut perm: Vec<usize> = (0..primes.len()).collect();
        while x < self.squares.len() && y < primes.len() {
            if !A[y][x] {
                for below in (y + 1)..primes.len() {
                    if A[below][x] {
                        LinearEquations::swap_row(&mut A, y, below);
                        perm.as_mut_slice().swap(y, below);
                        break;
                    }
                }
                if !A[y][x] {
                    free_variables.push(x);
                    x += 1;
                    continue;
                }
            }
            for below in (y + 1)..primes.len() {
                if A[below][x] {
                    LinearEquations::add_row(&mut A, y, below);
                }
            }
            none_trivial.push(x);
            x += 1;
            y += 1;
        }
        while x < self.squares.len() {
            free_variables.push(x)
        }
        for combination in Combination::new(free_variables.len() as u32) {
            let mut res = vec![false; self.squares.len()];
            for i in 0..free_variables.len() {
                res[free_variables[i]] = combination[i];
            }
            for var in none_trivial.iter().rev() {
                let mut state = false;
                for i in (var + 1)..self.squares.len() {
                    state ^= res[i] && A[*var][i];
                }
                res[*var] = state;
            }
            let mut first: BigUint = One::one();
            let mut second: BigUint = One::one();
            for i in 0..self.squares.len() {
                if res[i] {
                    let (big, small, _) = &self.squares[i];
                    first *= big;
                    second *= small;
                }
            }
            second = second.sqrt();
            let gcd = LinearEquations::gcd(first.clone() - second.clone(),
                                           self.base.clone());
            if !gcd.is_one() && gcd != self.base {
                return Ok((gcd.clone(), self.base.clone() / gcd));
            }
        }
        Err("Not found")
    }
}

fn read_values<T: FromStr>(s: &str) -> Result<Vec<T>, T::Err> {
    s.trim().split_whitespace().map(|word| word.parse()).collect()
}

fn u8_to_bool(a:u8) -> bool{
    a == 1
}

#[wasm_bindgen]
pub fn compute_linear_equations(smooth: u32, base: &str, data: &str) -> String
{
    let mut parsed = vec![];
    let base_num = match BigUint::parse_bytes(base.as_bytes(), INPUT_BASE){
        Some(num) => num,
        None => return "ERROR WRONG BASE STRING".to_string(),
    };
    let squares = data.split('$');
    for sq in squares{
        let splitted = sq.trim().split(";").collect::<Vec<&str>>();
        if splitted.len() != 3{
            return "Wrong input".to_string();
        }
        let big = match BigUint::parse_bytes(splitted[0].trim().as_bytes(), INPUT_BASE){
            Some(num) => num,
            None => return "ERROR WRONG BIG STRING".to_string(),
        };
        let small = match BigUint::parse_bytes(splitted[1].trim().as_bytes(), INPUT_BASE){
            Some(num) => num,
            None => return "ERROR WRONG SMALL STRING".to_string(),
        };
        let parity : Vec<u8> = match read_values(splitted[2].trim()){
            Ok(data) => data,
            Err(_) => return "ERROR WRONG PARITY STRING".to_string(),
        };
        let bool_parity : Vec<bool >= parity.into_iter().map(u8_to_bool).collect();
        parsed.push((big, small, bool_parity));
    }
    let linear = LinearEquations::new(smooth, base_num, parsed);
    match linear.find_solution(){
        Ok((num1, num2)) => (format!("{};{}", num1, num2).to_string()),
        Err(error) => error.to_string()
    }
}

#[cfg(test)]
mod test{
    use crate::linear_equations::compute_linear_equations;

    #[test]
    fn test1(){
        let data = "735;352;1 0 0 0 1 0 0$
            750;22627;0 0 0 0 1 0 1$
            783;73216;1 0 0 0 1 1 0$
            801;101728;1 0 0 0 1 0 0";
        println!("{}", compute_linear_equations(17, "539873", data));
    }
}



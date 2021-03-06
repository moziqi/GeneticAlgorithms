package com.ga.util;

/**
 * Rosenbrock个体实现
 * 
 * @author 邓锴
 *
 */
public class RosenbrockIndividual extends Individual {
	private double x1, x2; // 个体表现型

	/**
	 * 基因型chromosome由 (x1 , x2)编码而成
	 * 
	 * @param chromlen
	 */
	RosenbrockIndividual(int chromlen) {
		genelen = 10;
		chrom = new Chromosome(chromlen);
	}

	/**
	 * 编码
	 */
	@Override
	public void coding() {
		String code1, code2;
		code1 = codingVariable(x1);
		code2 = codingVariable(x2);
		chrom.setGene(0, 9, code1);
		chrom.setGene(0, 9, code2);
	}

	/**
	 * 解码
	 */
	@Override
	public void decode() {
		String gene1, gene2;

		gene1 = chrom.getGene(0, 9);
		gene2 = chrom.getGene(0, 9);

		x1 = decodeGene(gene1);
		x2 = decodeGene(gene2);
	}

	/**
	 * 计算目标函数值
	 */
	@Override
	public void calTargetValue() {
		decode();
		targetValue = rosenbrock(x1, x2);
	}

	/**
	 * 计算个体适应度
	 */
	@Override
	public void calFitness() {
		fitness = getTargetValue();
	}

	@Override
	public String toString() {
		String str = "";
		str = "基因型:" + chrom + "\t";
//		str += "表现型:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";
		str += "适应度:" + rosenbrock(x1, x2) + "\n";
		return str;
	}

	private String codingVariable(double x) {
		double y = (((x + 2.048) * 1023) / 4.096);
		String code = Integer.toBinaryString((int) y);

		StringBuffer codeBuf = new StringBuffer(code);
		//修改过这里的值
		for (int i = code.length(); i < genelen; i++)
			codeBuf.insert(0, '1');

		return codeBuf.toString();
	}

	private double decodeGene(String gene) {
		int value;
		double decode;
		value = Integer.parseInt(gene, 2);
		decode = value / 1023.0 * 4.096 - 2.048;
		return decode;
	}

	/**
	 * 适应度 Rosenbrock函数: f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2
	 * 在当x在[-2.048 , 2.048]内时， 函数有两个极大点: f(2.048 , -2.048) = 3897.7342
	 * f(-2.048,-2.048) = 3905.926227 其中后者为全局最大点。
	 */
	public static double rosenbrock(double x1, double x2) {
		double fun;
		fun = 100 * Math.pow((x1 * x1 - x2), 2) + Math.pow((1 - x1), 2);
		return fun;
	}

	/**
	 * 随机产生个体
	 */
	public void generateIndividual() {

		x1 = getRandom();
		x2 = getRandom();

		// 同步编码和适应度
		coding();
		calTargetValue();
		calFitness();
	}

	public double getRandom() {
		double result = Math.random() * 4.096 - 2.048;
		System.out.println(result);
		return result;
	}
}
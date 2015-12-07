package simiam.robot.sensor.noise;

public class GaussianNoise extends NoiseModel {
	private double mean;
   private double standard_deviation;
	
	public GaussianNoise(double mean,double sigma){
		this.mean=mean;
       this.standard_deviation=sigma;
	}
	@Override
	public double apply_noise(double data) {
		double noise = mean + standard_deviation*Math.random();
		return data + noise;
	}

}

from gradio_client import Client
import configparser

def text_prompt(prompt,image_number = 1):
	config = configparser.ConfigParser()
	config.read("config.ini")
	url = config["ngrok"]["url"]
	print(url)

	client = Client(url,serialize=False)

	result = client.predict(
					False,	# bool in 'Disable Preview' Checkbox component
					1.5,	# int | float (numeric value between 0.1 and 3.0)
									#in 'Positive ADM Guidance Scaler' Slider component
					0.8,	# int | float (numeric value between 0.1 and 3.0)
									#in 'Negative ADM Guidance Scaler' Slider component
					0.3,	# int | float (numeric value between 0.0 and 1.0)
									#in 'ADM Guidance End At Step' Slider component
					7,	# int | float (numeric value between 1.0 and 30.0)
									#in 'CFG Mimicking from TSNR' Slider component
					"dpmpp_2m_sde_gpu",	# str (Option from: ['euler', 'euler_ancestral', 'heun', 'heunpp2', 'dpm_2', 'dpm_2_ancestral', 'lms', 'dpm_fast', 'dpm_adaptive', 'dpmpp_2s_ancestral', 'dpmpp_sde', 'dpmpp_sde_gpu', 'dpmpp_2m', 'dpmpp_2m_sde', 'dpmpp_2m_sde_gpu', 'dpmpp_3m_sde', 'dpmpp_3m_sde_gpu', 'ddpm', 'lcm', 'ddim', 'uni_pc', 'uni_pc_bh2'])
									#in 'Sampler' Dropdown component
					"karras",	# str (Option from: ['normal', 'karras', 'exponential', 'sgm_uniform', 'simple', 'ddim_uniform', 'lcm', 'turbo'])
									#in 'Scheduler' Dropdown component
					False,	# bool in 'Generate Image Grid for Each Batch' Checkbox component
					-1,	# int | float (numeric value between -1 and 200)
									#in 'Forced Overwrite of Sampling Step' Slider component
					-1,	# int | float (numeric value between -1 and 200)
									#in 'Forced Overwrite of Refiner Switch Step' Slider component
					-1,	# int | float (numeric value between -1 and 2048)
									#in 'Forced Overwrite of Generating Width' Slider component
					-1,	# int | float (numeric value between -1 and 2048)
									#in 'Forced Overwrite of Generating Height' Slider component
					-1,	# int | float (numeric value between -1 and 1.0)
									#in 'Forced Overwrite of Denoising Strength of "Vary"' Slider component
					-1,	# int | float (numeric value between -1 and 1.0)
									#in 'Forced Overwrite of Denoising Strength of "Upscale"' Slider component
					False,	# bool in 'Mixing Image Prompt and Vary/Upscale' Checkbox component
					False,	# bool in 'Mixing Image Prompt and Inpaint' Checkbox component
					False,	# bool in 'Debug Preprocessors' Checkbox component
					False,	# bool in 'Skip Preprocessors' Checkbox component
					0.25,	# int | float (numeric value between 0.0 and 1.0)
									#in 'Softness of ControlNet' Slider component
					64,	# int | float (numeric value between 1 and 255)
									#in 'Canny Low Threshold' Slider component
					128,	# int | float (numeric value between 1 and 255)
									#in 'Canny High Threshold' Slider component
					"joint",	# str (Option from: ['joint', 'separate', 'vae'])
									#in 'Refiner swap method' Dropdown component
					False,	# bool in 'Enabled' Checkbox component
					None,	# int | float (numeric value between 0 and 2)
									#in 'B1' Slider component
					None,	# int | float (numeric value between 0 and 2)
									#in 'B2' Slider component
					None,	# int | float (numeric value between 0 and 4)
									#in 'S1' Slider component
					None,	# int | float (numeric value between 0 and 4)
									#in 'S2' Slider component
					False,	# bool in 'Debug Inpaint Preprocessing' Checkbox component
					False,	# bool in 'Disable initial latent in inpaint' Checkbox component
					"v2.6",	# str (Option from: ['None', 'v1', 'v2.5', 'v2.6'])
									#in 'Inpaint Engine' Dropdown component
					1,	# int | float (numeric value between 0.0 and 1.0)
									#in 'Inpaint Denoising Strength' Slider component
					0.618,	# int | float (numeric value between 0.0 and 1.0)
									#in 'Inpaint Respective Field' Slider component
					False,	# bool in 'Enable Mask Upload' Checkbox component
					False,	# bool in 'Invert Mask' Checkbox component
					0,	# int | float (numeric value between -64 and 64)
									#in 'Mask Erode or Dilate' Slider component
					fn_index=32
	)
	print(result)

	output = client.predict(prompt, #prompt
							"",#negative prompt
							["Fooocus V2","Fooocus Enhance","Fooocus Sharp"],#styles
							"Speed",#performance
							"1152×896 1:2",#ratio
							image_number,#image number
							-1,#seed
							2.0,#image sharpness
							4.0,#guidance switch
							"juggernautXL_version6Rundiffusion.safetensors",#base model
							"None",#refiner
							0.5,#refiner_scale
							"sd_xl_offset_example-lora_1.0.safetensors",#lora1
							0.1,#weight1
							None,#Lora2
							1,#weight2
							None,#Lora3
							1,#weight3
							None,#Lora4
							1,#weight4
							None,#Lora5
							1,#weight5
							False,#input image
							"Howdy!",	# str in 'parameter_85' Textbox component
							"Disabled",	# str in 'Upscale or Variation:' Radio component
							None,	# str (filepath or URL to image)
							["Left"],	# List[str] in 'Outpaint Direction' Checkboxgroup component
							None,	# str (filepath or URL to image)
							"Howdy!",	# str in 'Inpaint Additional Prompt' Textbox component
							None,	# str (filepath or URL to image)
							None,	# str (filepath or URL to image)
							0,	# int | float (numeric value between 0.0 and 1.0)
							0,	# int | float (numeric value between 0.0 and 2.0)
							"ImagePrompt",	# str in 'Type' Radio component
							None,	# str (filepath or URL to image)
							0,	# int | float (numeric value between 0.0 and 1.0)
							0,	# int | float (numeric value between 0.0 and 2.0)
							"ImagePrompt",	# str in 'Type' Radio component
							None,	# str (filepath or URL to image)
							0,	# int | float (numeric value between 0.0 and 1.0)
							0,	# int | float (numeric value between 0.0 and 2.0)
							"ImagePrompt",	# str in 'Type' Radio component
							None,	# str (filepath or URL to image)
							0,	# int | float (numeric value between 0.0 and 1.0)
							0,	# int | float (numeric value between 0.0 and 2.0)
							"ImagePrompt",	# str in 'Type' Radio component
							fn_index=33)

	print(output)

	from urllib.request import urlretrieve

	file = ""
	for value in output:
		if value["visible"]:
			file = value["value"][0]["name"]
	print(file)
	return(url+"file="+file)

"""image = urlretrieve(url+"file="+file)

from PIL import Image
img = Image.open(image[0])
img.show()"""


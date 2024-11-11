import asyncio
import subprocess

async def run_command(command):
    process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = await asyncio.to_thread(process.communicate)
    if process.returncode == 0:
        print(f"Comando '{command}' ejecutado con éxito")
    else:
        print(f"Error al ejecutar '{command}': {stderr.decode()}")

async def run_parallel_commands():
    # Definir las rutas absolutas para los subdirectorios
    estudiantes_dir = r"C:\Users\ignah\Desktop\Carpeta\Facultad\Diseño de Software\tpIntegradorMicroservicios\estudiantes"
    inscripciones_dir = r"C:\Users\ignah\Desktop\Carpeta\Facultad\Diseño de Software\tpIntegradorMicroservicios\inscripciones"

    # Comandos en paralelo
    tasks = [
        run_command(f"cd {estudiantes_dir} && mvn clean package -DskipTests"),
        run_command(f"cd {estudiantes_dir} && mvn clean install -DskipTests"),
        run_command(f"cd {inscripciones_dir} && mvn clean package -DskipTests"),
        run_command(f"cd {inscripciones_dir} && mvn clean install -DskipTests")
    ]
    
    # Ejecutar en paralelo
    await asyncio.gather(*tasks)
    
    # Una vez que terminen ambos, ejecutar docker
    await run_command("docker compose build")
    await run_command("docker compose up")

# Ejecutar los comandos
asyncio.run(run_parallel_commands())

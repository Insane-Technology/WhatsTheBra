var particles = {
    fpsLimit: 60,
    particles: {
        number: {
            value: 160,
            density: {
                enable: true,
                area: 800
            }
        },
        color: {
            value: "#ffffff"
        },
        shape: {
            type: "circle"
        },
        opacity: {
            value: 1,
            random: {
                enable: true,
                minimumValue: 0.1
            },
            animation: {
                enable: true,
                speed: 2,
                minimumValue: 0,
                sync: false
            }
        },
        size: {
            value: 6,
            random: {
                enable: true,
                minimumValue: 1
            }
        },
        move: {
            enable: true,
            speed: 1.0,
            direction: "none",
            random: true,
            straight: false,
            outModes: {
                default: "out"
            },
        }
    },
    interactivity: {
        detectsOn: "canvas",
        events: {
            resize: false
        }
    },
    detectRetina: true
};

tsParticles.load("tsparticles", particles);

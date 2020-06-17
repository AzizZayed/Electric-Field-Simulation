<style TYPE="text/css">
code.has-jax {font: inherit; font-size: 100%; background: inherit; border: inherit;}
</style>
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
    tex2jax: {
        inlineMath: [['$','$'], ['\\(','\\)']],
        skipTags: ['script', 'noscript', 'style', 'textarea', 'pre'] // removed 'code' entry
    }
});
MathJax.Hub.Queue(function() {
    var all = MathJax.Hub.getAllJax(), i;
    for(i = 0; i < all.length; i += 1) {
        all[i].SourceElement().parentNode.className += ' has-jax';
    }
});
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-AMS_HTML-full"></script>

# Electric-Field-Simulation

This is a simulation of an electric field with an arbitrary number of charges with Java Graphics and discrete [vector fields](https://en.wikipedia.org/wiki/Vector_field). 

Instructions:
* Press the 'r' key to restart
* Use the scroll wheel to change the resolution of the vector field
* Move the charges by clicking and dragging them

___

Physics:

The magnitude of the electric field at a specific point caused by a charge is calculated with the following equation

$$E=\frac{kQ}{d^2}$$

Where\
$Q$ is the charge magnitude in Coulombs ($C$)\
$k$ is Coulombs constant (doesn't matter for this simulation)\
$d$ is the distance between the point and the charge

The individual vector components are then calculated:
$$E_x=E\cos{\theta} = E\frac{d_x}{d}$$
$$E_y=E\sin{\theta} = E\frac{d_y}{d}$$

Where $\theta$ would be the angle created between the charge and the point.

Electric fields for single charges should look like this:

![](singlecharge_theory.png)

And as you can see, when you increase the resolution in my simulator, you start to see a similar pattern:

![](singlecharge.jpg)


___

It looks like this when the vector field is dense: 

![](dense-field.gif)

And like this when less dense:

![](field.gif)

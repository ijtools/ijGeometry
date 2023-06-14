# ij_Geometry
Geometry library for ImageJ.

This library provides a set of data classes and tools for geometry computing, 
to facilitate the management of 2D/3D geometric shapes within ImageJ plugins.

## Aims and scope

The aim is to gather all ImageJ-related geometry questions into a single library, 
that can be used for the development of more specialized / finalized plugins.
Typcial examples include but are not limited to:

* extraction of geometric primitives (ellipses, polygons, 3D meshes...) from 2D/3D digital images
* facilitate geometric operation on ROI (regions of intersets)
* generating synthetic shapes for testing or validating image analysis algorithms and methods
* register images of shapes by computing the most suited geometric deformation
* ...

In its current state, the library contains several classes for representation of common (geometry)
data structures. More specialized classes and operations will be integrated according to the needs.

## Installation

The project is based on maven. It uses sci-java as parent configuration. The parent configuration 
is somewhat old (1.126), but I encountered configuration troubles with more recent ones. 

The base configuration has few dependencies:

* ImageJ
* JUnit

